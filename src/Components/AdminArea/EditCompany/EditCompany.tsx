import { useForm } from "react-hook-form";
import "./EditCompany.css";
import CompanyModel from "../../../Models/CompanyModel";
import adminService from "../../../Services/AdminService";
import notificationService from "../../../Services/NotificationService";
import { NavLink, useNavigate, useParams } from "react-router-dom";
import { useEffect } from "react";
import { faCircleArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

function EditCompany(): JSX.Element {


    const { register, handleSubmit, formState, setValue } = useForm<CompanyModel>();

    const params = useParams();
    const companyId = +params.companyId;

    const navigate = useNavigate();

    useEffect(() => {
      adminService.getCompany(companyId).then((c)=>{
           setValue("id",companyId); 
           setValue("name",c.name); 
           setValue("email",c.email); 
           setValue("password",c.password); 
        }).catch((err)=>{notificationService.error(err)});

    }, []);

    async function send(company: CompanyModel) {
        try {
            console.log(company);
            await adminService.updateCompany(company);
            notificationService.success("The company was updated");
            navigate("/admin/companies/")
        } catch (error: any) {
            notificationService.error(error);
        }
    }

    return (
        <div className="EditCompany Box">

            <form onSubmit={handleSubmit(send)}>

                <h2>Edit Company:</h2>

                <label>Name: </label>
                <span>{formState.errors?.name?.message}</span>
                <input disabled type="text" {...register("name", { required: { value: true, message: "please enter name" } })} />

                <label>Email: </label>
                <span>{formState.errors?.email?.message}</span>
                <input type="email" {...register("email", { required: { value: true, message: "please enter email" } })} />

                <label>Password: </label>
                <span>{formState.errors?.password?.message}</span>
                <input type="password" {...register("password", { required: { value: true, message: "please enter password" } })} />

                <button>Save</button>

            </form>
            <NavLink id="back" to={"/home"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back </NavLink>

        </div>
    );
}

export default EditCompany;
