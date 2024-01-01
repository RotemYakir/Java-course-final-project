import { NavLink } from "react-router-dom";
import "./CustomerMenu.css";

function CustomerMenu(): JSX.Element {
    return (
        <div className="CustomerMenu">
						<NavLink to="/customer/coupons/">My Coupons</NavLink>
						<NavLink to="/coupons/">Purchase Coupon</NavLink>
        </div>
    );
}

export default CustomerMenu;
