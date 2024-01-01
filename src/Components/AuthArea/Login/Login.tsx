import { useForm } from "react-hook-form";
import "./Login.css";
import CredentialsModel from "../../../Models/CredentialsModel";
import authService from "../../../Services/AuthService";
import notificationService from "../../../Services/NotificationService";
import { useNavigate } from "react-router-dom";
import { SetStateAction, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";



function Login(): JSX.Element {

    const [userType, setUserType] = useState<string>("admin");

    const { register, handleSubmit, formState } = useForm<CredentialsModel>();
    const navigate = useNavigate();

    async function send(credentials: CredentialsModel) {
        try {
            await authService.login(credentials, userType);
            notificationService.success("Welcome Back!");
            navigate("/" + userType);
        } catch (error) {
            notificationService.error(error);
        }
    }

    const [inputType, setInputType] = useState('password');
    const [icon, setIcon] = useState(<FontAwesomeIcon icon={faEyeSlash} />);
    const handleToggle = () => {
        if (inputType === 'password') {
            setIcon(<FontAwesomeIcon icon={faEye} />);
            setInputType('text');
        } else {
            setIcon(<FontAwesomeIcon icon={faEyeSlash} />);
            setInputType('password');
        }
    }

    return (
        <div className="Login Box" >
            <form onSubmit={handleSubmit(send)}>

                <h2>Login</h2>
                login as:  <select onChange={(e) => setUserType(e.target.value)} >
                    <option value="admin">Admin</option>
                    <option value="company">Company</option>
                    <option value="customer">Customer</option>
                </select>
                <br /><br />
                <label>Email</label>
                <span>{formState.errors?.email?.message}</span>
                <input type="email"{...register("email", { required: { value: true, message: "please enter email" } })} />
                <label>Password</label>
                <span>{formState.errors?.password?.message}</span>
                <input type={inputType} {...register("password", { required: { value: true, message: "please enter password" } })} />
                <span onClick={handleToggle} id="icon">{icon}</span>
                <button>Login 🔓</button>
            </form>
        </div>
    );
}

export default Login;
