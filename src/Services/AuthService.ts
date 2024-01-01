import axios from "axios";
import CredentialsModel from "../Models/CredentialsModel";
import appConfig from "../Utils/Config";
import { authStore, loginAction, logoutAction } from "../Redux/AuthState";

class AuthService {

    async login(credentials: CredentialsModel,clientType:string): Promise<void> {
        const response = await axios.post<string>(appConfig.loginUrl+clientType, credentials);
        const token = response.data;
        authStore.dispatch(loginAction(token));
    }


    public logout(): void {
        authStore.dispatch(logoutAction());
    }

}

const authService = new AuthService();
export default authService;