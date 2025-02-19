import {request} from '@/plugins/axios'
import router from "@/router";
import {getCookie} from "typescript-cookie";

function getLoginState(): void {
    request.post('/user/getLoginState/?token=' + getCookie('token'), {}).then(r => {
        console.log(Boolean(r.data))

        if (getCookie('token') != undefined) {
            if (Boolean(r.data)) {
                router.replace("/points");
            }else {
                router.replace("/");
            }
        }else {
            router.replace("/");
        }
    });
}

export default getLoginState;