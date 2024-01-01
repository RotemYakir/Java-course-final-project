import { useEffect, useState } from "react";
import CouponModel from "../../../Models/CouponModel";
import "./CouponsListCustomer.css";
import customerService from "../../../Services/CustomerService";
import notificationService from "../../../Services/NotificationService";
import CouponCard from "../../CardsArea/CouponCard/CouponCard";
import CustomerCategoryFilterMenu from "../CustomerCategoryFilterMenu/CustomerCategoryFilterMenu";
import CustomerSearchByPrice from "../CustomerSearchByPrice/CustomerSearchByPrice";
import { faCircleArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { NavLink } from "react-router-dom";

function CouponsListCustomer(): JSX.Element {

    const [coupons, setCoupons] = useState<CouponModel[]>([]);
    const [couponsByCategory, setCcouponsByCategory] = useState<CouponModel[]>([]);
    const [couponsByPrice, setCcouponsByPrice] = useState<CouponModel[]>([]);

    useEffect(() => {
        customerService.getCoupons()
            .then((arr) => { setCoupons(arr) })
            .catch((err) => { notificationService.error(err) });
    }, []);

    return (
        <div className="CouponsListCustomer">
            <h2>Your coupons are:</h2>
            <CustomerCategoryFilterMenu />
            <CustomerSearchByPrice/>
            <br />
            {coupons.length > 0 && (<>
                {coupons.map((c) => (
                    <CouponCard key={c.id} coupon={c} />
                ))}
            </>)}
            {coupons.length === 0 && (<><h3 id="no_coupons">You don't have coupons at the moment</h3></>)}
            <NavLink id="back" to={"/home"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back </NavLink>
        </div>
    );
}

export default CouponsListCustomer;
