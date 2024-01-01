import axios from "axios";
import CouponModel from "../Models/CouponModel";
import appConfig from "../Utils/Config";
import { addCouponAction, companyStore, deleteCouponAction, fetchCompanyDetailsAction, fetchCouponsAction, updateCouponAction } from "../Redux/CompanyState";
import Category from "../Models/Enums/Category";
import CompanyModel from "../Models/CompanyModel";
import { addAction, couponsStore, deleteAction, updateAction } from "../Redux/couponsState";

class CompanyService {

    public async getDetails(): Promise<CompanyModel> {
        if (!companyStore.getState().company) {
            const response = await axios.get<CompanyModel>(appConfig.companyGetDetailsUrl);
            companyStore.dispatch(fetchCompanyDetailsAction(response.data));
            return response.data;
        }
        return companyStore.getState().company;
    }

    public async addCoupon(coupon: CouponModel): Promise<void> {
        coupon.company = await companyService.getDetails();
        const response = await axios.post<CouponModel>(appConfig.companyAddCouponUrl, coupon);
        companyStore.dispatch(addCouponAction(response.data));
        couponsStore.dispatch(addAction(response.data));
    };

    public async updateCoupon(coupon: CouponModel): Promise<void> {
        coupon.company = await companyService.getDetails();
        const response = await axios.put<CouponModel>(appConfig.companyUpdateCouponUrl, coupon);
        companyStore.dispatch(updateCouponAction(response.data));
        couponsStore.dispatch(updateAction(response.data));
    }


    public async deleteCoupon(couponId: number): Promise<void> {
        await axios.delete(appConfig.companyDeleteCouponUrl + couponId);
        companyStore.dispatch(deleteCouponAction(couponId));
        couponsStore.dispatch(deleteAction(couponId));
    }


    public async getCoupon(couponId: number): Promise<CouponModel> {
        if (companyStore.getState().coupons.length === 0) {
            const coupons = await companyService.getAllCoupons();
            return coupons.find((c) => c.id === couponId);
        }
        return companyStore.getState().coupons.find((c) => c.id === couponId);
    }


    public async getAllCoupons(): Promise<CouponModel[]> {
        if (companyStore.getState().coupons.length === 0) {
            const response = await axios.get<CouponModel[]>(appConfig.companyGetAllCouponsUrl);
            const coupons = response.data;
            companyStore.dispatch(fetchCouponsAction(coupons));
            return coupons;
        }
        return companyStore.getState().coupons;
    }

}

const companyService = new CompanyService();

export default companyService;