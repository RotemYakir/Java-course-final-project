import { useNavigate } from "react-router-dom";
import "./Logout.css";
import { useEffect } from "react";
import authService from "../../../Services/AuthService";
import notificationService from "../../../Services/NotificationService";
import { authStore } from "../../../Redux/AuthState";

function Logout(): JSX.Element {

    const navigate = useNavigate();

    useEffect(() => {
        authService.logout();
        notificationService.success("Bye Bye");
        navigate("/home");
    }, []);

    return null;
}

export default Logout;
