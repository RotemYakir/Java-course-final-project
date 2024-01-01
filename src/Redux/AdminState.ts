import { createStore } from "redux";
import CompanyModel from "../Models/CompanyModel";
import CustomerModel from "../Models/CustomerModel";

export class AdminState {
    public companies: CompanyModel[] = [];
    public customers: CustomerModel[] = [];
}

export enum adminActionType {
    FetchCompanies, AddCompany, UpdateCompany, DeleteCompany,
    FetchCustomers, AddCustomer, UpdateCustomer, DeleteCustomer
}

export interface AdminAction {
    type: adminActionType;
    payload: any;
}

export function fetchcompaniesAction(companies: CompanyModel[]): AdminAction {
    return { type: adminActionType.FetchCompanies, payload: companies };
}
export function addCompanyAction(company: CompanyModel): AdminAction {
    return { type: adminActionType.AddCompany, payload: company };
}
export function updateCompanyAction(company: CompanyModel): AdminAction {
    return { type: adminActionType.UpdateCompany, payload: company };
}
export function deleteCompanyAction(companyId: number): AdminAction {
    return { type: adminActionType.DeleteCompany, payload: companyId };
}
export function fetchCustomersAction(customers: CustomerModel[]): AdminAction {
    return { type: adminActionType.FetchCustomers, payload: customers };
}
export function addCustomerAction(customer: CustomerModel): AdminAction {
    return { type: adminActionType.AddCustomer, payload: customer };
}
export function updateCustomerAction(customer: CustomerModel): AdminAction {
    return { type: adminActionType.UpdateCustomer, payload: customer };
}
export function deleteCustomerAction(customerId: number): AdminAction {
    return { type: adminActionType.DeleteCustomer, payload: customerId };
}


export function adminReducer(currentState: AdminState = new AdminState(), action: AdminAction): AdminState {
    const newState = { ...currentState };
    switch (action.type) {
        case adminActionType.FetchCompanies:
            newState.companies = action.payload;
            break;
        case adminActionType.FetchCustomers:
            newState.customers = action.payload;
            break;
        case adminActionType.AddCompany:
            newState.companies.push(action.payload);
            break;
        case adminActionType.AddCustomer:
            newState.customers.push(action.payload);
            break;
        case adminActionType.UpdateCompany:
            const indexToUpdateCompany = newState.companies.findIndex(c => c.id === action.payload.id);
            if (indexToUpdateCompany>=0) newState.companies[indexToUpdateCompany] = action.payload;
            break;
        case adminActionType.UpdateCustomer:
            const indexToUpdateCustomer = newState.customers.findIndex(c => c.id === action.payload.id);
            if (indexToUpdateCustomer>=0) newState.customers[indexToUpdateCustomer] = action.payload;
            break;
            case adminActionType.DeleteCompany: 
            const indexToDeleteCompany = newState.companies.findIndex(c => c.id === action.payload);
            if (indexToDeleteCompany >= 0) newState.companies.splice(indexToDeleteCompany, 1);
            break;
            case adminActionType.DeleteCustomer: 
            const indexToDeleteCustomer = newState.customers.findIndex(c => c.id === action.payload);
            if (indexToDeleteCustomer >= 0) newState.customers.splice(indexToDeleteCustomer, 1);
            break;
    }
    return newState;
}


export const adminStore = createStore(adminReducer);