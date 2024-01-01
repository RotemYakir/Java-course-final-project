import { NavLink } from "react-router-dom";
import "./Menu.css";
import { faHouse} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

function Menu(): JSX.Element {
    return (
        <div className="Menu">
            <NavLink to="/home"><FontAwesomeIcon icon={faHouse} /> Home</NavLink>
            <span>|</span>
            <NavLink to="/about"> About us</NavLink>
        </div>
    );
}

export default Menu;
