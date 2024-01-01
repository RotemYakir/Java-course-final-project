import { NavLink } from "react-router-dom";
import "./CategoryFilterMenu.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faShirt,faUtensils,faPlane,faTruckMedical, faPlug,} from '@fortawesome/free-solid-svg-icons';

function CategoryFilterMenu(): JSX.Element {

    return (
        <div className="CategoryFilterMenu ">
            <NavLink className={"Box"} id="clothing" to={"/coupons/clothing"}><FontAwesomeIcon icon={faShirt} /> Clothing</NavLink>
            <NavLink className={"Box"} id="restaurant" to={"/coupons/restaurant"}><FontAwesomeIcon icon={faUtensils} /> Restaurant</NavLink>
            <NavLink className={"Box"} id="vacation" to={"/coupons/vacation"}><FontAwesomeIcon icon={faPlane} /> Vacation</NavLink>
            <NavLink className={"Box"} id="farmacy" to={"/coupons/farmacy"}><FontAwesomeIcon icon={faTruckMedical} /> Pharmacy</NavLink>
            <NavLink className={"Box"} id="electricity" to={"/coupons/electricity"}><FontAwesomeIcon icon={faPlug} /> Electricity</NavLink>
        </div>
    );
}

export default CategoryFilterMenu;
