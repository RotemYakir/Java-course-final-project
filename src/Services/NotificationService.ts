import { Notyf } from "notyf";

class NotificationService {

    private notify = new Notyf({ duration: 5000, position: { x: "center", y: "top" } });

    public success(message: string) {
        this.notify.success(message);
    }

    public error(err: any) {
        console.dir(err);
        const message = this.extractErrorMessage(err);
        this.notify.error(message);
    }

    private extractErrorMessage(err: any) {
        if (typeof err === "string") return err;        

        if (typeof err.response?.data?.message === "string") return err.response.data.message;        

        if (Array.isArray(err.response?.data)) return err.response.data[0]; 

         if (typeof err.message === "string") return err.message;         

        console.dir(err)
        return "Unknown error. please try again."
    }

}

const notificationService = new NotificationService();
export default notificationService;