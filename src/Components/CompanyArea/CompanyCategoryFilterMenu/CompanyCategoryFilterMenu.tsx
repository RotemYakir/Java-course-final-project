import { faShirt, faUtensils, faPlane, faTruckMedical, faPlug } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { NavLink } from "react-router-dom";
import "./CompanyCategoryFilterMenu.css";

function CompanyCategoryFilterMenu(): JSX.Element {
    return (
        <div className="CompanyCategoryFilterMenu">
			<NavLink className={"Box"} id="clothing" to={"/company/coupons/clothing"}><FontAwesomeIcon icon={faShirt} /> Clothing</NavLink>
            <NavLink className={"Box"} id="restaurant" to={"/company/coupons/restaurant"}><FontAwesomeIcon icon={faUtensils} /> Restaurant</NavLink>
            <NavLink className={"Box"} id="vacation" to={"/company/coupons/vacation"}><FontAwesomeIcon icon={faPlane} /> Vacation</NavLink>
            <NavLink className={"Box"} id="farmacy" to={"/company/coupons/farmacy"}><FontAwesomeIcon icon={faTruckMedical} /> Pharmacy</NavLink>
            <NavLink className={"Box"} id="electricity" to={"/company/coupons/electricity"}><FontAwesomeIcon icon={faPlug} /> Electricity</NavLink>
        </div>
    );
}

export default CompanyCategoryFilterMenu;
