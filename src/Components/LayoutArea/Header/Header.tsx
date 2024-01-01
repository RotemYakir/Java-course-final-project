import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import AuthMenu from "../../AuthArea/AuthMenu/AuthMenu";
import {faFaceLaughBeam} from "@fortawesome/free-solid-svg-icons";
import "./Header.css";
import Menu from "../Menu/Menu";


function Header(): JSX.Element {
    return (
        <div className="Header">
            <h1>Coup<FontAwesomeIcon icon={faFaceLaughBeam} />n System</h1>
            <AuthMenu />
            <Menu/>
        </div>
    );
}

export default Header;
