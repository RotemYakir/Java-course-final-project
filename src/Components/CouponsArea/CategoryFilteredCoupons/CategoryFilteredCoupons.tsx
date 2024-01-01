import { useParams } from "react-router-dom";
import "./CategoryFilteredCoupons.css";
import { useEffect, useState } from "react";
import CouponModel from "../../../Models/CouponModel";
import customerService from "../../../Services/CustomerService";
import notificationService from "../../../Services/NotificationService";
import CouponCard from "../../CardsArea/CouponCard/CouponCard";
import { NavLink } from "react-router-dom";
import { faCircleArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

function CategoryFilteredCoupons(): JSX.Element {

    const params = useParams();
    const category = params.category;

    const [coupons, setCoupons] = useState<CouponModel[]>([]);

    useEffect(() => {
        customerService.getAllCoupons()
            .then((arr) => { setCoupons(arr.filter((c)=>{return c.category == category.toUpperCase()}))})
            .catch((err) => { notificationService.error(err) });
    }, []);


    return (
        <div className="CategoryFilteredCoupons">
                <h2>{category} coupons: </h2>
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

export default CategoryFilteredCoupons;
