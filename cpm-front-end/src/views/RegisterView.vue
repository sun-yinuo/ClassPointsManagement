<template>
  <div class="register-container">
    <div class="register-box">
      <div class="register-header">
        <h1>注册新账号</h1>
        <p>创建您的个人账号</p>
      </div>
      <Form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-form">
        <FormItem prop="username">
          <Input v-model="registerForm.username" prefix="ios-person" placeholder="用户名" />
        </FormItem>
        <FormItem prop="password">
          <Input v-model="registerForm.password" prefix="ios-lock" type="password" placeholder="密码" />
        </FormItem>
        <FormItem prop="confirmPassword">
          <Input v-model="registerForm.confirmPassword" prefix="ios-lock" type="password" placeholder="确认密码" />
        </FormItem>
        <FormItem>
          <Button type="primary" long @click="handleRegister">注册</Button>
        </FormItem>
      </Form>
      <div class="register-footer">
        <p>已有账号？<a @click="goToLogin">立即登录</a></p>
      </div>
    </div>
  </div>
</template>

<script>
import router from "@/router";
import {getCookie, setCookie} from "typescript-cookie";
import {request} from "@/plugins/axios";
import {ref} from "vue";

let message = ref("");
export default {
  data() {
    return {
      registerForm: {
        username: '',
        password: '',
        confirmPassword: ''
      },
      registerRules: {
        username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
        password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
        confirmPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: this.validateConfirmPassword, trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    validateConfirmPassword(rule, value, callback) {
      if (value !== this.registerForm.password) {
        callback(new Error('两次输入的密码不一致'));
      } else {
        callback();
      }
    },
    handleRegister() {
      request.post('/user/register', {
        "userName": this.registerForm.username,
        "password": this.registerForm.password
      }).then(r => {
        message.value = r.data.msg;
        if (r.data.code === 200) {
          this.$Notice.success({
            title: '注册成功',
            desc: message.value,
          });
          setCookie('token',r.data.data);
          // noinspection JSIgnoredPromiseFromCall
          router.replace('/login');
        }
        if (r.data.code === 403) {
          this.$Notice.error({
            title: '注册失败',
            desc: message.value,
          });
        }

      });
    },
    goToLogin() {
      this.$router.push('/login');
    }
  },
  created() {
    request.post('/user/getLoginState/?token=' + getCookie('token'), {}).then(r => {
      if (getCookie('token') !== undefined) {
        if (Boolean(r.data)) {
          router.replace("/points");
        }
      }
    });
  }
};
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, rgba(173, 216, 230, 0.8), rgba(240, 248, 255, 0.8), rgba(173, 216, 230, 0.8));
  background-size: 200% 200%;
  animation: gradientFlow 10s ease infinite;
}

.register-box {
  background: rgba(255, 255, 255, 0.9);
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
  width: 100%;
  max-width: 400px;
  animation: fadeIn 1s ease;
}

.register-header {
  text-align: center;
  margin-bottom: 20px;
}

.register-header h1 {
  font-size: 24px;
  color: #333;
}

.register-header p {
  font-size: 14px;
  color: #666;
}

.register-form {
  margin-top: 20px;
}

.register-footer {
  text-align: center;
  margin-top: 20px;
}

.register-footer a {
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