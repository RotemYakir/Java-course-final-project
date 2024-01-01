import { createStore } from "redux";
import CouponModel from "../Models/CouponModel"
import CompanyModel from "../Models/CompanyModel";

export class CompanyState {
    coupons: CouponModel[] = [];
    company: CompanyModel;
}

export enum companyActionType {
    FetchCoupons,
    AddCoupon,
    UpdateCoupon,
    DeleteCoupon,
    FetchCompanyDetails
}

export interface CompanyAction {
    type: companyActionType;
    payload?: any;
}

export function fetchCouponsAction(coupons: CouponModel[]): CompanyAction {
    return { type: companyActionType.FetchCoupons, payload: coupons };
}

export function addCouponAction(coupon: CouponModel): CompanyAction {
    return { type: companyActionType.AddCoupon, payload: coupon };
}

export function updateCouponAction(coupon: CouponModel): CompanyAction {
    return { type: companyActionType.UpdateCoupon, payload: coupon };
}

export function deleteCouponAction(couponId: number): CompanyAction {
    return { type: companyActionType.DeleteCoupon, payload: couponId };
}

export function fetchCompanyDetailsAction(company:CompanyModel): CompanyAction {
    return { type: companyActionType.FetchCompanyDetails, payload: company };
}


export function companyReducer(currentState: CompanyState = new CompanyState(), action: CompanyAction): CompanyState {
    const newState = { ...currentState };
    switch (action.type) {
        case companyActionType.FetchCoupons:
            newState.coupons = action.payload;
            break;
        case companyActionType.AddCoupon:
            newState.coupons.push(action.payload);
            break;
        case companyActionType.UpdateCoupon:
            const indexToUpdate = newState.coupons.findIndex(c => c.id === action.payload.id);
            if (indexToUpdate >= 0) newState.coupons[indexToUpdate] = action.payload;
            break;
        case companyActionType.DeleteCoupon:
            const indexToDelete = newState.coupons.findIndex(c => c.id === action.payload);
            if (indexToDelete >= 0) newState.coupons.splice(indexToDelete, 1);
            break;
        case companyActionType.FetchCompanyDetails:
            newState.company = action.payload;
            break;
    }
    return newState;
}

export const companyStore = createStore(companyReducer);