<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
        <h1>欢迎登录</h1>
        <p>请输入您的账号和密码</p>
      </div>
      <Form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
        <FormItem prop="username">
          <Input v-model="loginForm.username" prefix="ios-person" placeholder="用户名" />
        </FormItem>
        <FormItem prop="password">
          <Input v-model="loginForm.password" prefix="ios-lock" type="password" placeholder="密码" />
        </FormItem>
        <FormItem>
          <Button type="primary" long @click="handleLogin">登录</Button>
        </FormItem>
      </Form>
      <div class="login-footer">
        <p>没有账号？<a @click="goToRegister">立即注册</a></p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {FormItem, Notice} from "view-ui-plus";
import {onMounted, ref} from "vue";

import {request} from "@/plugins/axios";
import router from "@/router";
import {getCookie, setCookie} from "typescript-cookie";
import getLoginState from "@/api/getLoginState";


let message = ref("");

export default {


  components: {FormItem},
  data() : { loginForm: { username: string; password: string }; loginRules: any }{
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
        password: [{ required: true, message: '密码不能为空', trigger: 'blur' }]
      }
    };
  },
  methods: {
    handleLogin(this: any){
      request.post('/user/login', {
        "userName": this.loginForm.username,
        "password": this.loginForm.password
      }).then(r => {
        message.value = r.data.msg;
        if (r.data.code === 200) {
          Notice.success({
            title: '登陆成功',
            desc: message.value,
          });
          setCookie('token',r.data.data);
          // noinspection JSIgnoredPromiseFromCall
          router.replace('/points');
        }
        if (r.data.code === 403) {
          Notice.error({
            title: '登陆失败',
            desc: message.value,
          });
        }

      });
    },
    goToRegister() {
      router.push('/register');
    },
  },
  created() {
    getLoginState()
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, rgba(173, 216, 230, 0.8), rgba(240, 248, 255, 0.8), rgba(173, 216, 230, 0.8));
  background-size: 200% 200%;
  animation: gradientFlow 10s ease infinite;
}

.login-box {
  background: rgba(255, 255, 255, 0.9);
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
  width: 100%;
  max-width: 400px;
  animation: fadeIn 1s ease;
}

.login-header {
  text-align: center;
  margin-bottom: 20px;
}

.login-header h1 {
  font-size: 24px;
  color: #333;
}

.login-header p {
  font-size: 14px;
  color: #666;
}

.login-form {
  margin-top: 20px;
}

.login-footer {
  text-align: center;
  margin-top: 20px;
}

.login-footer a {
  color: #2575fc;
  cursor: pointer;
}

@keyframes gradientFlow {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(-20px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>