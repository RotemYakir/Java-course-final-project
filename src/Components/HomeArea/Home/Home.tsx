import { useEffect, useState } from "react";
import "./Home.css";
import UserModel from "../../../Models/UserModel";
import { authStore } from "../../../Redux/AuthState";
import ClientType from "../../../Models/Enums/ClientType";
import Company from "../Company/Company";
import Admin from "../Admin/Admin";
import Customer from "../Customer/Customer";
import Login from "../../AuthArea/Login/Login";


function Home(): JSX.Element {
    const [user, setUser] = useState<UserModel>();

    useEffect(() => {
        setUser(authStore.getState().user);
    }, []);


    return (
        <div className="Home">
            {!user &&
                <>
                    <h2>Welcome to our coupon system 👋</h2>
                    <Login />
                </>
            }

            {user && user.clientType.toString() === "COMPANY" &&
                <Company />
            }
            {user && user.clientType.toString() === "ADMIN" &&
                <Admin />
            }
            {user && user.clientType.toString() === "CUSTOMER" &&
                <Customer />
            }



        </div>
    );
}

export default Home;
