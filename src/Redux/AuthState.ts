import jwtDecode from "jwt-decode";
import UserModel from "../Models/UserModel";
import { createStore } from "redux";
import { companyStore } from "./CompanyState";
import { customerStore } from "./CustomerState";

export class AuthState {
    token: string = null;
    user: UserModel = null;

    constructor() {
        this.token = localStorage.getItem("token");
        if (this.token) {
            const decodedToken: UserModel = jwtDecode(this.token);
            this.user = decodedToken;
        }
    }

}

export enum AuthActionType {
    Login, Logout
}

export interface AuthAction {
    type: AuthActionType;
    payload?: any;
}

export function loginAction(token: string) {
    return { type: AuthActionType.Login, payload: token };
}

export function logoutAction() {
    return { type: AuthActionType.Logout };
}

export function authReducer(currentState = new AuthState(), action: AuthAction): AuthState {
    const newState = { ...currentState };
    switch (action.type) {
        case AuthActionType.Login:
            newState.token = action.payload;
            const decodedToken: UserModel = jwtDecode(newState.token);
            newState.user = decodedToken;
            localStorage.setItem("token", newState.token);
            break;
        case AuthActionType.Logout:
            companyStore.getState().coupons.length = 0;
            customerStore.getState().coupons.length = 0;
            newState.token = null;
            newState.user = null;
            localStorage.removeItem("token");
            break;
    }
    return newState;

}

export const authStore = createStore(authReducer);


