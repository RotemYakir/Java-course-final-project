import { NavLink, useNavigate, useParams } from "react-router-dom";
import "./EditCustomer.css";
import { useForm } from "react-hook-form";
import CustomerModel from "../../../Models/CustomerModel";
import { useEffect } from "react";
import adminService from "../../../Services/AdminService";
import notificationService from "../../../Services/NotificationService";
import { faCircleArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

function EditCustomer(): JSX.Element {

    const { register, handleSubmit, formState, setValue } = useForm<CustomerModel>();

    const navigate = useNavigate();

    const params = useParams();
    const customerId = +params.customerId;

    useEffect(() => {
        adminService.getCustomer(customerId).then((c) => {
            setValue("id", customerId);
            setValue("firstName", c.firstName);
            setValue("lastName", c.lastName);
            setValue("email", c.email);
            setValue("password", c.password);
        }).catch((err) => { notificationService.error(err) })
    }, []);

    async function send(customer: CustomerModel) {
        try {
            console.log(customer);
            await adminService.updateCustomer(customer);
            notificationService.success("The customer was updated");
            navigate("");
        } catch (error: any) {
            notificationService.error(error);
        }
    }

    return (
        <div className="EditCustomer Box">
            <form onSubmit={handleSubmit(send)}>

                <h2>Update Customer:</h2>

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

                <button>Update</button>
            </form>

            <NavLink id="back" to={"/home"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back </NavLink>

        </div>
    );
}

export default EditCustomer;
