import { useEffect, useState } from "react";
import CouponModel from "../../../Models/CouponModel";
import "./CouponsList.css";
import customerService from "../../../Services/CustomerService";
import notificationService from "../../../Services/NotificationService";
import CouponCard from "../../CardsArea/CouponCard/CouponCard";
import CategoryFilteredCoupons from "../CategoryFilteredCoupons/CategoryFilteredCoupons";
import CategoryFilterMenu from "../CategoryFilterMenu/CategoryFilterMenu";
import SearchByPrice from "../SearchByPrice/SearchByPrice";
import { faCircleArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { NavLink } from "react-router-dom";

function CouponsList(): JSX.Element {

    const [coupons, setCoupons] = useState<CouponModel[]>([]);


    useEffect(() => {
        customerService.getAllCoupons()
            .then((arr) => { setCoupons(arr) })
            .catch((err) => { notificationService.error(err) });
    }, []);


    return (
        <div className="CouponsList">
            <h2>Choose a Coupon: </h2>
            <CategoryFilterMenu />
            <SearchByPrice />
            <br />
            {coupons.length > 0 && (<>
                {coupons.map((c) => (
                    <CouponCard key={c.id} coupon={c} />
                ))}
            </>)}
            {coupons.length === 0 && (<><h3 id="no_coupons">There are no coupons available at the moment</h3></>)}
            <NavLink id="back" to={"/home"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back </NavLink>
        </div>
    );
}

export default CouponsList;
