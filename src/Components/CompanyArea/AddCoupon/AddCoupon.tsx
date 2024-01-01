import { useForm } from "react-hook-form";
import "./AddCoupon.css";
import CouponModel from "../../../Models/CouponModel";
import companyService from "../../../Services/CompanyService";
import notificationService from "../../../Services/NotificationService";
import { useNavigate } from "react-router-dom";
import { NavLink } from "react-router-dom";
import { faCircleArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

function AddCoupon(): JSX.Element {

    const { register, handleSubmit, formState } = useForm<CouponModel>();

    const navigate = useNavigate();

    async function send(coupon: CouponModel) {
        try {
            await companyService.addCoupon(coupon);
            notificationService.success("your coupon was added successfully");
            navigate("/company/coupons/");
        } catch (error: any) {
            notificationService.error(error);
        }
    }

  
   return (
        <div className="AddCoupon Box">
            <form onSubmit={handleSubmit(send) } >
                <h2>Add a new coupon:</h2>

                <label>Title: </label>
                <span>{formState.errors?.title?.message}</span>
                <input type="text" {...register("title", { required: { value: true, message: "please enter title" } })} />

                <label>Description: </label>
                <span>{formState.errors?.description?.message}</span>
                <input type="text" {...register("description", { required: { value: true, message: "please enter a description" } })} />

                <label>Category: </label>
                <select {...register("category")} >
                    <option value="ELECTRICITY">Electricity</option>
                    <option value="RESTAURANT">Restaurant</option>
                    <option value="VACATION">Vacation</option>
                    <option value="FARMACY">Farmacy</option>
                    <option value="CLOTHING">Clothing</option>
                </select>
                <label>Start Date: </label>
                <span>{formState.errors?.startDate?.message}</span>
                <input type="date" name="startDate" {...register("startDate", { required: { value: true, message: "please choose a start date" } })} />
                <label>Expiration Date: </label>
                <span>{formState.errors?.endDate?.message}</span>
                <input type="date" name="endDate" {...register("endDate", { required: { value: true, message: "please choose an expiration date" } })} />
                <label>Amount: </label>
                <span>{formState.errors?.amount?.message}</span>
                <input type="number" {...register("amount", { required: { value: true, message: "please enter amount value" }, min: { value: 0, message: "amount can't be negative" } })} />
                <label>Price: </label>
                <span>{formState.errors?.price?.message}</span>
                <input type="number" step={"0.01"} {...register("price", { required: { value: true, message: "please enter coupon price" }, min: { value: 0, message: "price can't be negative" } })} />
                <label>image: </label>
                <span>{formState.errors?.image?.message}</span>
                <input type="link" {...register("image", { required: { value: true, message: "missing image" } })} />
                <button>Add</button>
            </form>
            <NavLink id="back" to={"/company/coupons/"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back </NavLink>

        </div>
    );
}

export default AddCoupon;