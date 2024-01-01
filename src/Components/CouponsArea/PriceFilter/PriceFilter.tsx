import { useEffect, useState } from "react";
import "./PriceFilter.css";
import { useParams } from "react-router-dom";
import customerService from "../../../Services/CustomerService";
import CouponModel from "../../../Models/CouponModel";
import notificationService from "../../../Services/NotificationService";
import { NavLink } from "react-router-dom";
import { faCircleArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import CouponCard from "../../CardsArea/CouponCard/CouponCard";

function PriceFilter(): JSX.Element {

    const params = useParams();
    const price = +params.price;

    const [coupons, setCoupons] = useState<CouponModel[]>([]);

    useEffect(() => {
        customerService.getAllCoupons()
        .then((arr) => { setCoupons(arr.filter((c) => {return c.price <= price}))})
        .catch((err) => { notificationService.error(err) });
    }, []);

    return (
        <div className="PriceFilter">
       <h2> All Coupons up to {price}$ : </h2>
           <NavLink id="back" to={"/coupons/"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back</NavLink>
            <br />
            {coupons.length > 0 && (<>
                {coupons.map((c) => (
                    <CouponCard key={c.id} coupon={c} />
                ))}
            </>)}
            {coupons.length === 0 && (<><h3 id="no_coupons">There are no coupons available at the moment</h3></>)}

        </div>
    );
}

export default PriceFilter;
