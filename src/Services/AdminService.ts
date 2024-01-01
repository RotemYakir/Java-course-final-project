import axios from "axios";
import CompanyModel from "../Models/CompanyModel";
import appConfig from "../Utils/Config";
import CustomerModel from "../Models/CustomerModel";
import { addCompanyAction, addCustomerAction, adminStore, deleteCompanyAction, deleteCustomerAction, fetchCustomersAction, fetchcompaniesAction, updateCompanyAction, updateCustomerAction } from "../Redux/AdminState";
import { authStore } from "../Redux/AuthState";

class AdminService {


    public async addCompany(company: CompanyModel): Promise<void> {
        const response = await axios.post<CompanyModel>(appConfig.adminAddCompanyUrl, company);
        adminStore.dispatch(addCompanyAction(response.data))
    }

    public async updateCompany(company: CompanyModel): Promise<void> {
        const response = await axios.put<CompanyModel>(appConfig.adminUpdateCompanyUrl, company);
        adminStore.dispatch(updateCompanyAction(response.data));
    }

    public async addCustomer(customer: CustomerModel): Promise<void> {
        const response = await axios.post<CustomerModel>(appConfig.adminAddCustomerUrl, customer);
        adminStore.dispatch(addCustomerAction(response.data));
    }

    public async updateCustomer(customer: CustomerModel): Promise<void> {
        const response = await axios.put<CustomerModel>(appConfig.adminUpdateCustomerUrl, customer);
        adminStore.dispatch(updateCustomerAction(response.data));
    }

    public async getCompany(id: number): Promise<CompanyModel> {
            if (adminStore.getState().companies.length === 0) {
                const response = await axios.get<CompanyModel>(appConfig.adminGetCompanyUrl + id);
                return response.data;
            }
            return adminStore.getState().companies.find((c) => c.id === id);
    }
        
        public async getCustomer(id: number): Promise<CustomerModel> {
            if (adminStore.getState().customers.length === 0) {
            const response = await axios.get<CustomerModel>(appConfig.adminGetCustomerUrl + id);
            return response.data;
        }
        return adminStore.getState().customers.find((c) => c.id === id);
    }

    public async deleteCompany(id: number): Promise<void> {
        await axios.delete(appConfig.adminDeleteCompanyUrl + id);
        adminStore.dispatch(deleteCompanyAction(id));
    }

    public async deleteCustomer(id: number): Promise<void> {
        await axios.delete(appConfig.adminDeleteCustomerUrl + id);
        adminStore.dispatch(deleteCustomerAction(id));
    }

    public async getAllCompanies(): Promise<CompanyModel[]> {
        if (adminStore.getState().companies.length === 0) {
            const response = await axios.get<CompanyModel[]>(appConfig.adminGetAllCompaniesUrl);
            adminStore.dispatch(fetchcompaniesAction(response.data));
            return response.data;
        }
        return adminStore.getState().companies;
    }


    public async getAllCustomers(): Promise<CustomerModel[]> {
        if (adminStore.getState().customers.length === 0) {
            const response = await axios.get<CustomerModel[]>(appConfig.adminGetAllCustomersUrl);
            adminStore.dispatch(fetchCustomersAction(response.data));
            return response.data;
        }
        return adminStore.getState().customers;
    }
}


const adminService = new AdminService();
export default adminService;