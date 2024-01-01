import { faShirt, faUtensils, faPlane, faTruckMedical, faPlug } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { NavLink } from "react-router-dom";
import "./CustomerCategoryFilterMenu.css";

function CustomerCategoryFilterMenu(): JSX.Element {
    return (
        <div className="CustomerCategoryFilterMenu">
			<NavLink className={"Box"} id="clothing" to={"/customer/coupons/clothing"}><FontAwesomeIcon icon={faShirt} /> Clothing</NavLink>
            <NavLink className={"Box"} id="restaurant" to={"/customer/coupons/restaurant"}><FontAwesomeIcon icon={faUtensils} /> Restaurant</NavLink>
            <NavLink className={"Box"} id="vacation" to={"/customer/coupons/vacation"}><FontAwesomeIcon icon={faPlane} /> Vacation</NavLink>
            <NavLink className={"Box"} id="farmacy" to={"/customer/coupons/farmacy"}><FontAwesomeIcon icon={faTruckMedical} /> Pharmacy</NavLink>
            <NavLink className={"Box"} id="electricity" to={"/customer/coupons/electricity"}><FontAwesomeIcon icon={faPlug} /> Electricity</NavLink>
        </div>
    );
}

export default CustomerCategoryFilterMenu;
