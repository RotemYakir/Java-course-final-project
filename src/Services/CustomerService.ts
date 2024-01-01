import axios from "axios";
import CustomerModel from "../Models/CustomerModel";
import appConfig from "../Utils/Config";
import { PurchaseCouponAction, customerStore, fetchCouponsAction } from "../Redux/CustomerState";
import CouponModel from "../Models/CouponModel";
import { couponsStore, fetchAllAction } from "../Redux/couponsState";

class CustomerService {


    public async getDetails(): Promise<CustomerModel> {
        const response = await axios.get<CustomerModel>(appConfig.customerGetDetailsUrl);
        return response.data;
    }

    public async purchaseCoupon(couponId: number): Promise<void> {
        await axios.put(appConfig.customerPurchaseCouponUrl + couponId);
        customerStore.dispatch(PurchaseCouponAction(couponId));
    }

    public async getCoupons(): Promise<CouponModel[]> {
        if (customerStore.getState().coupons.length === 0) {
            const response = await axios.get<CouponModel[]>(appConfig.customerGetCouponsUrl);
            const coupons = response.data;
            customerStore.dispatch(fetchCouponsAction(coupons));
            return coupons;
        }
        return customerStore.getState().coupons;
    }
    public async getAllCoupons(): Promise<CouponModel[]> {
        if (couponsStore.getState().coupons.length === 0) {
            const response = await axios.get<CouponModel[]>(appConfig.customerGetAllCouponsUrl);
            couponsStore.dispatch(fetchAllAction(response.data));
            return response.data;
        }
        return couponsStore.getState().coupons;
    }

}

const customerService = new CustomerService();
export default customerService;