import { useEffect, useState } from "react";
import "./CouponsListCompany.css";
import CouponModel from "../../../Models/CouponModel";
import companyService from "../../../Services/CompanyService";
import CouponCard from "../../CardsArea/CouponCard/CouponCard";
import notificationService from "../../../Services/NotificationService";
import { NavLink } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCircleArrowLeft } from "@fortawesome/free-solid-svg-icons";
import CompanyCategoryFilterMenu from "../CompanyCategoryFilterMenu/CompanyCategoryFilterMenu";
import CompanySearchByPrice from "../CompanySearchByPrice/CompanySearchByPrice";


function CouponsListCompany(): JSX.Element {

    const [coupons, setCoupons] = useState<CouponModel[]>([]);

    useEffect(() => {
        companyService.getAllCoupons()
            .then((arr) => { setCoupons(arr) })
            .catch((err) => { notificationService.error(err) });
    }, []);


    return (
        <div className="CouponsListCompany">
            <NavLink id="add" to={"/company/coupons/new"}>New Coupon ➕</NavLink>
            <h2>Your coupons are:</h2>
            <CompanyCategoryFilterMenu/>
            <CompanySearchByPrice/>
            <br />
            <div>
                <NavLink id="back" to={"/company/"}><FontAwesomeIcon icon={faCircleArrowLeft} /> Back </NavLink>
            </div>
            {coupons.length > 0 && (<>
                {coupons.map((c) => (
                    <CouponCard key={c.id} coupon={c} />
                ))}
            </>)}
            {coupons.length === 0 && (<><h3 id="no_coupons">You don't have coupons at the moment</h3></>)}
        </div>
    );
}

export default CouponsListCompany;
