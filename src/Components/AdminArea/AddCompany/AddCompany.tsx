import { useForm } from "react-hook-form";
import "./AddCompany.css";
import CompanyModel from "../../../Models/CompanyModel";
import adminService from "../../../Services/AdminService";
import notificationService from "../../../Services/NotificationService";
import { NavLink, useNavigate } from "react-router-dom";
import { faCircleArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

function AddCompany(): JSX.Element {


    const { register, handleSubmit, formState } = useForm<CompanyModel>();
    const navigate = useNavigate();

    async function send(company: CompanyModel) {
        try {
            await adminService.addCompany(company);
            notificationService.success("The company was added successfully");
            navigate("/admin/companies/");
            
        } catch (error: any) {
            notificationService.error(error);
        }
    }

    return (
        <div className="AddCompany Box">

            <form onSubmit={handleSubmit(send)}>

                <h2>Add New Company:</h2>

                <label>Name: </label>
                <span>{formState.errors?.name?.message}</span>
                <input type="text" {...register("name", { required: { value: true, message: "please enter name" } })} />

                <label>Email: </label>
                <span>{formState.errors?.email?.message}</span>
                <input type="email" {...register("email", { required: { value: true, message: "please enter email" } })} />

                <label>Password: </label>
                <span>{formState.errors?.password?.message}</span>
                <input type="password" {...register("password", { required: { value: true, message: "please enter password" } })} />

                <button>Add</button>


                <NavLink id="back" to={"/home"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back </NavLink>

            </form>

        </div>
    );
}

export default AddCompany;
