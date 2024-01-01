import { useEffect, useState } from "react";
import "./CategoryFilteredCustomer.css";
import { useParams } from "react-router-dom";
import CouponModel from "../../../Models/CouponModel";
import customerService from "../../../Services/CustomerService";
import notificationService from "../../../Services/NotificationService";
import { NavLink } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCircleArrowLeft } from "@fortawesome/free-solid-svg-icons";
import CouponCard from "../../CardsArea/CouponCard/CouponCard";

function CategoryFilteredCustomer(): JSX.Element {

    const params = useParams();
    const category = params.category;

    const [coupons, setCoupons] = useState<CouponModel[]>([]);

    useEffect(() => {
        customerService.getCoupons()
            .then((arr) => { setCoupons(arr.filter((c) => { return c.category == category.toUpperCase() })) })
            .catch((err) => { notificationService.error(err) });
    }, []);


    return (
        <div className="CategoryFilteredCustomer">
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

export default CategoryFilteredCustomer;
