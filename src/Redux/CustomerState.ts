import { createStore } from "redux";
import CouponModel from "../Models/CouponModel"
import { couponsStore } from "./couponsState";

export class customerState {
    public coupons: CouponModel[]=[];
}

export enum CustomerActionType {
    FetchCoupons,
    PurchaseCoupon
}

export interface CustomerAction {
    type: CustomerActionType;
    payload: any;
}

export function fetchCouponsAction(coupons: CouponModel[]): CustomerAction {
    return { type: CustomerActionType.FetchCoupons, payload: coupons };
}

export function PurchaseCouponAction(couponId: number): CustomerAction {
    return { type: CustomerActionType.PurchaseCoupon, payload: couponId };
}

export function customerReducer(currentState: customerState = new customerState(), action: CustomerAction): customerState {
    const newState = { ...currentState };
    switch (action.type) {
        case CustomerActionType.FetchCoupons:
            newState.coupons = action.payload;
            break;
        case CustomerActionType.PurchaseCoupon:
            const coupon = couponsStore.getState().coupons.find((c) => c.id === action.payload);
            coupon.amount-=1;
            newState.coupons.push(coupon);
            break;
    }
    return newState;
}


export const customerStore = createStore(customerReducer);