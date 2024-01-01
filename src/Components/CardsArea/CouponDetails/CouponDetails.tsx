import { useNavigate, useParams } from "react-router-dom";
import "./CouponDetails.css";
import { useEffect, useState } from "react";
import CouponModel from "../../../Models/CouponModel";
import companyService from "../../../Services/CompanyService";
import { NavLink } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCircleArrowLeft, faFilePen, faTrashCan, faCartShopping } from "@fortawesome/free-solid-svg-icons";
import notificationService from "../../../Services/NotificationService";
import { authStore } from "../../../Redux/AuthState";
import ClientType from "../../../Models/Enums/ClientType";
import customerService from "../../../Services/CustomerService";


function CouponDetails(): JSX.Element {

    const params = useParams();
    const couponId = +params.couponId;

    const [coupon, setCoupon] = useState<CouponModel>();
  

    const navigate = useNavigate();

    useEffect(() => {
        if (authStore.getState().user.clientType.toString() === ClientType[ClientType.COMPANY]) {
            companyService.getCoupon(couponId).then((c) => { setCoupon(c) }).catch((err) => { notificationService.error(err) });
        }
        if (authStore.getState().user.clientType.toString() === ClientType[ClientType.CUSTOMER]) {
            customerService.getAllCoupons().then((arr) => {
                setCoupon(arr.find((c) => c.id === couponId))
            }).catch((err) => { notificationService.error(err) });
        };
      

    }, []);
    async function purchaseCoupon() {
        try {
            if (window.confirm("Buy This Coupon?")) {
                await customerService.purchaseCoupon(couponId);
                notificationService.success("congradulations! the coupon was added to your list.");
                navigate("/customer/coupons");
            }
        } catch (err: any) {
            notificationService.error(err);
        }
    }

    async function deleteCoupon() {
        try {
            if (window.confirm("Are you sure you want to delete this coupon?")) {
                await companyService.deleteCoupon(couponId);
                notificationService.success("the coupon was deleted");
                navigate("/company/coupons");
            }
        } catch (err: any) {
            notificationService.error(err);
        }
    }


    return (
        <div className="CouponDetails Box">

            {coupon &&
                (<>
                    {authStore.getState().user.clientType.toString() === ClientType[ClientType.COMPANY] &&
                        (<><NavLink id="delete" to={""} onClick={deleteCoupon}>Delete <FontAwesomeIcon icon={faTrashCan} style={{ color: "#190b0b", }} /></NavLink>
                            <NavLink id="edit" to={"/company/coupons/edit/" + couponId}>Edit  <FontAwesomeIcon icon={faFilePen} /></NavLink>
                            <NavLink id="back" to={"/company/coupons/"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back </NavLink></>)}

                    {authStore.getState().user.clientType.toString() === ClientType[ClientType.CUSTOMER] &&
                        (<> <NavLink id="buy" to={""} onClick={purchaseCoupon} >{coupon.price}$ Buy  <FontAwesomeIcon icon={faCartShopping} /></NavLink></>)}

                    <div id="details">
                        Title: {coupon?.title}<br />
                        Description: {coupon?.description}<br />
                        Category: {coupon?.category.toString().toLowerCase()}<br />
                        Amount: {coupon?.amount} <br />
                        Start date: {coupon?.startDate.toString()}<br />
                        Expiration: {coupon?.endDate.toString()} <br />
                        Price: {coupon?.price.toString()}$
                    </div>
                    <div>
                        <img src={coupon.image} alt="" />
                    </div>
                </>)
            }
        </div>
    );
}

export default CouponDetails;
