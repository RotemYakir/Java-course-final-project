import { NavLink } from "react-router-dom";
import "./CompanyMenu.css";

function CompanyMenu(): JSX.Element {
    return (
        <div className="CompanyMenu">
			<NavLink to="/company/coupons/new">Add Coupon</NavLink>
			<NavLink to="/company/coupons/">Display Coupons</NavLink>
        </div>
    );
}

export default CompanyMenu;
