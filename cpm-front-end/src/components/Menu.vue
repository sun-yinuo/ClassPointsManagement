<template>
  <Menu mode="horizontal" :theme="theme" active-name="1">
    <MenuItem name="1" to="/points">
      <Icon type="ios-ribbon" />
      积分管理
    </MenuItem>
    <MenuItem name="2" to="/ranking">
      <Icon type="ios-people" />
      积分排行榜
    </MenuItem>
    <MenuItem name="3" to="/event" v-if="showItem">
      <Icon type="md-analytics" />
      事件管理
    </MenuItem>
    <MenuItem name="4" to="/audit" v-if="showItem">
      <Icon type="md-information-circle" />
      审核面板
    </MenuItem>
  </Menu>
</template>

<script>
import {Notice} from "view-ui-plus";
import { onMounted, ref } from "vue";
import { request } from "@/plugins/axios";
import { getCookie } from "typescript-cookie";
import router from "@/router";

export default {
  setup() {
    const showItem = ref(false);

    // 获取用户权限
    const checkShowButton = () => {
      request.get('/user/getAdministratorByToken/?token=' + getCookie('token'))
          .then(r => {
            let result = r.data;

            if (result === 2) {
              showItem.value = true;
            } else if (result === 0 || result === 1) {
              showItem.value = false;
            } else if (result === -1) {
              Notice.error({
                title: '未登录',
              });
              router.replace('/login');
            }
          })
          .catch(() => {
            Notice.error({
              title: '请求失败，请重试',
            });
          });
    };

    // 组件挂载后执行
    onMounted(() => {
      checkShowButton();
    });

    return {
      showItem
    };
  }
};
</script>
