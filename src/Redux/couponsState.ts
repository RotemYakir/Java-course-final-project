import { createStore } from "redux";
import CouponModel from "../Models/CouponModel";

export class CouponsState {
    public coupons: CouponModel[] = [];
}

export enum couponsActionType {
    FetchCoupons,
    AddCoupon,
    UpdateCoupon,
    DeleteCoupon,
}

export interface CouponsAction {
    type: couponsActionType
    payload: any;
}

export function fetchAllAction(coupons: CouponModel[]): CouponsAction {
    return { type: couponsActionType.FetchCoupons, payload: coupons }
}
export function addAction(coupon: CouponModel): CouponsAction {
    return { type: couponsActionType.AddCoupon, payload: coupon };
}
export function updateAction(coupon: CouponModel): CouponsAction {
    return { type: couponsActionType.UpdateCoupon, payload: coupon };
}
export function deleteAction(couponId: number): CouponsAction {
    return { type: couponsActionType.DeleteCoupon, payload: couponId };
}


export function couponsReducer(currentState: CouponsState = new CouponsState(), action: CouponsAction): CouponsState {
    const newState = { ...currentState };
    switch (action.type) {
        case couponsActionType.FetchCoupons:
            newState.coupons = action.payload;
            break;
        case couponsActionType.AddCoupon:
            newState.coupons.push(action.payload)
            break;
        case couponsActionType.UpdateCoupon:
            const indexToUpdate = newState.coupons.findIndex(c => c.id === action.payload.id);
            if (indexToUpdate >= 0) newState.coupons[indexToUpdate] = action.payload;
            break;
        case couponsActionType.DeleteCoupon:
            const indexToDelete = newState.coupons.findIndex(p => p.id === action.payload);
            if (indexToDelete >= 0) newState.coupons.splice(indexToDelete, 1);
            break;
    }
    return newState;
}

export const couponsStore = createStore(couponsReducer);