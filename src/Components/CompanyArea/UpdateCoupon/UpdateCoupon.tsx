import { useEffect, useState } from "react";
import "./UpdateCoupon.css";
import companyService from "../../../Services/CompanyService";
import { useNavigate, useParams } from "react-router-dom";
import CouponModel from "../../../Models/CouponModel";
import { useForm } from "react-hook-form";
import notificationService from "../../../Services/NotificationService";
import { NavLink } from "react-router-dom";
import { faCircleArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

function UpdateCoupon(): JSX.Element {

    const { register, handleSubmit, formState, setValue } = useForm<CouponModel>();

    const params = useParams();
    const couponId = +params.couponId;

    const navigate = useNavigate();

    useEffect(() => {
        companyService.getCoupon(couponId).then((coupon) => {
            setValue("id", couponId);
            setValue("title", coupon.title);
            setValue("description", coupon.description);
            setValue("category", coupon.category);
            setValue("startDate", coupon.startDate);
            setValue("endDate", coupon.endDate);
            setValue("amount", coupon.amount);
            setValue("price", coupon.price);
            setValue("image", coupon.image);
        }).catch((err) => { notificationService.error(err) });
    }, []);


    async function send(coupon: CouponModel) {
        try {
            await companyService.updateCoupon(coupon);
            notificationService.success("updated");
            navigate("/company/coupons/");
        } catch (error: any) {
            notificationService.error(error);
        }
    }

    return (
        <div className="UpdateCoupon Box">
            <form onSubmit={handleSubmit(send)} >
                <h2>Edit coupon:</h2>
                <label>Title: </label>
                <span>{formState.errors?.title?.message}</span>
                <input type="text" {...register("title", { required: { value: true, message: "missing title" } })} />

                <label>Description: </label>
                <span>{formState.errors?.description?.message}</span>
                <input type="text" {...register("description", { required: { value: true, message: "missing description" } })} />

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
                <input type="date" name="startDate" {...register("startDate", { required: { value: true, message: "missing start date" } })} />
                <label>Expiration Date: </label>
                <span>{formState.errors?.endDate?.message}</span>
                <input type="date" name="endDate" {...register("endDate", { required: { value: true, message: "missing expiration" } })} />
                <label>Amount: </label>
                <span>{formState.errors?.amount?.message}</span>
                <input type="number" {...register("amount", { required: { value: true, message: "missing amount value" }, min: { value: 0, message: "amount can't be negative" } })} />
                <label>Price: </label>
                <span>{formState.errors?.price?.message}</span>
                <input type="number" step={"0.01"} {...register("price", { required: { value: true, message: "missing coupon price" }, min: { value: 0, message: "price can't be negative" } })} />
                <label>image Link: </label>
                <span>{formState.errors?.image?.message}</span>
                <input type="link" {...register("image", { required: { value: true, message: "missing image" } })} />
                <button>Save</button>
            </form>
            <NavLink id="back" to={"/company/coupons"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back </NavLink>
        </div>

    );
}

export default UpdateCoupon;