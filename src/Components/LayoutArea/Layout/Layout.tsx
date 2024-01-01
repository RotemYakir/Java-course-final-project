import { useEffect, useState } from "react";
import Footer from "../Footer/Footer";
import Header from "../Header/Header";
import Menu from "../Menu/Menu";
import Routing from "../Routing/Routing";
import "./Layout.css";
import UserModel from "../../../Models/UserModel";
import { authStore } from "../../../Redux/AuthState";

function Layout(): JSX.Element {

    const [user, setUser] = useState<UserModel>();

    useEffect(() => {
        setUser(authStore.getState().user);

        const unsubscribe = authStore.subscribe(() => {
            setUser(authStore.getState().user);
        });

        return () => {
            unsubscribe();
        }

    }, []);

    return (
        <div className="Layout">
            <header><Header /></header>
            <main><Routing /></main>
            <footer><Footer /></footer>
        </div>
    );
}

export default Layout;
