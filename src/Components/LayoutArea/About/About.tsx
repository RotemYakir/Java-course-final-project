import { faPencil } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./About.css";

function About(): JSX.Element {
    return (
        <div className="About Box">
            ... <FontAwesomeIcon icon={faPencil} />
            <p>
                Dear visitors, <br />
                this website was created in order to help a young programmer pass her <br />
                final test studying Java- full stack development. <br />
                We hope you enjoy and like our website. 😃

                <p>
                    Kind Regards, <br />
                    Rotem.

                </p>
            </p>
        </div>
    );
}

export default About;
