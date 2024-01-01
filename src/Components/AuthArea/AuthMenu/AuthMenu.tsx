import { useEffect, useState } from "react";
import "./AuthMenu.css";
import UserModel from "../../../Models/UserModel";
import { authStore } from "../../../Redux/AuthState";
import { NavLink } from "react-router-dom";

function AuthMenu(): JSX.Element {

    const [user, setUser] = useState<UserModel>();
    
    useEffect(() => {
        setUser(authStore.getState().user);

        const unsubscribe = authStore.subscribe(() => {
            setUser(authStore.getState().user);            
        });
      
        return ()=>{
            unsubscribe();
        }

    }, []);


    return (
        <div className="AuthMenu">

            {!user &&
                <>
                    <span>Hello Guest</span>
                    <NavLink to={"/login"}>Login</NavLink>
                </>
            }
            {user &&
                <>
                    <span>Hello  {user.clientType.toString().toLocaleLowerCase()}</span>
                    <NavLink to={"/logout"}>Logout</NavLink>
                </>
            }

        </div>
    );
}

export default AuthMenu;
