import { useForm } from "react-hook-form";
import CustomerModel from "../../../Models/CustomerModel";
import adminService from "../../../Services/AdminService";
import notificationService from "../../../Services/NotificationService";
import "./AddCustomer.css";
import { faCircleArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { NavLink } from "react-router-dom";

function AddCustomer(): JSX.Element {

    const { register, handleSubmit, formState } = useForm<CustomerModel>();

    async function send(customer: CustomerModel) {
        try {
            await adminService.addCustomer(customer);
            notificationService.success("The customer was added successfully");
        } catch (error: any) {
            notificationService.error(error);
        }
    }

    return (
        <div className="AddCustomer Box">
            <form onSubmit={handleSubmit(send)}>

                <h2>Add New Customer:</h2>

                <label>First Name: </label>
                <span>{formState.errors?.firstName?.message}</span>
                <input type="text" {...register("firstName", { required: { value: true, message: "please enter first name" } })} />

                <label>Last Name: </label>
                <span>{formState.errors?.lastName?.message}</span>
                <input type="text" {...register("lastName", { required: { value: true, message: "please enter last name" } })} />

                <label>Email: </label>
                <span>{formState.errors?.email?.message}</span>
                <input type="email" {...register("email", { required: { value: true, message: "please enter email" } })} />

                <label>Password: </label>
                <span>{formState.errors?.password?.message}</span>
                <input type="password" {...register("password", { required: { value: true, message: "please enter password" } })} />

                <button>Add</button>
            </form>

            <NavLink id="back" to={"/home"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back </NavLink>

        </div>
    );
}

export default AddCustomer;
