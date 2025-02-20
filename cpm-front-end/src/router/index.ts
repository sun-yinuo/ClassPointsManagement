import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

import LoginView from "@/views/LoginView.vue";
import RegisterView from "@/views/RegisterView.vue";
import PointView from "@/views/PointView.vue";
import AuditView from "@/views/AuditView.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/points',
    name: 'points',
    component: PointView
  },
  {
    path: '/audit',
    name: 'audit',
    component: AuditView
  }


]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
