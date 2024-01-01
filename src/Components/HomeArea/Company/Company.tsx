import { NavLink } from "react-router-dom";
import AddCoupon from "../../CompanyArea/AddCoupon/AddCoupon";
import CouponsListCompany from "../../CompanyArea/CouponsListCompany/CouponsListCompany";
import "./Company.css";

function Company(): JSX.Element {
    return (
        <div className="Company Box">
            <h2>What would you like to do?</h2>
            <NavLink id="coupons" to={"/company/coupons"}>I want to view and edit my coupons list</NavLink>
            <NavLink id="add" to={"/company/coupons/new"}>I want to add a new coupon</NavLink>
        </div>
    );
}

export default Company;
