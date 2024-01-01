import { faCircleArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState, useEffect } from "react";
import { useParams, NavLink } from "react-router-dom";
import CouponModel from "../../../Models/CouponModel";
import customerService from "../../../Services/CustomerService";
import notificationService from "../../../Services/NotificationService";
import CouponCard from "../../CardsArea/CouponCard/CouponCard";
import "./CompanyPriceFilter.css";
import companyService from "../../../Services/CompanyService";

function CompanyPriceFilter(): JSX.Element {

    const params = useParams();
    const price = +params.price;

    const [coupons, setCoupons] = useState<CouponModel[]>([]);

    useEffect(() => {
        companyService.getAllCoupons()
            .then((arr) => { setCoupons(arr.filter((c) => { return c.price <= price })) })
            .catch((err) => { notificationService.error(err) });
    }, []);

    return (
        <div className="CompanyPriceFilter">
                <h2> All Coupons up to {price}$ : </h2>
                <NavLink id="back" to={"/company/coupons/"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back</NavLink>
                <br />
                {coupons.length > 0 && (<>
                    {coupons.map((c) => (
                        <CouponCard key={c.id} coupon={c} />
                    ))}
                </>)}
                {coupons.length === 0 && (<><h3 id="no_coupons">There are no coupons available at the moment</h3></>)}
{}
            </div>
            );
}

            export default CompanyPriceFilter;
