import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '../views/Home.vue'
import Layout from '../modules/layout/index'

Vue.use(VueRouter)
// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err);
};

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../modules/login/views/login')
  },
  {
    path: '/',
    component: Layout,
    redirect: '/main',
    name: 'Main',
    children: [
      {
        path: 'main',
        component: () => import('../modules/main/views/main')
      }
    ]
  },
  {
    path: '/register',
    component: Layout,
    redirect: '/register/index',
    name: 'Register',
    children: [
      {
        path: 'index',
        component: () => import('../modules/register/views/register')
      },
      {
        path: 'examList',
        component: () => import('../modules/register/views/examList')
      }
    ]
  },
  {
    path: '/registrationQuery',
    component: Layout,
    redirect: '/registrationQuery/index',
    name: 'RegistrationQuery',
    children: [
      {
        path: 'index',
        component: () => import('../modules/registrationQuery/views/registrationQuery')
      },
      {
        path:'payPage',
        component:()=>import('../modules/registrationQuery/views/payPage')
      }
    ]
  },
  {
    path: '/examInfo',
    component: Layout,
    redirect: '/examInfo/index',
    name: 'ExamInfo',
    children: [
      {
        path: 'index',
        component: () => import('../modules/examInfo/views/examInfo')
      }
    ]
  },
  {
    path: '/resultsInquiry',
    component: Layout,
    redirect: '/resultsInquiry/index',
    name: 'ResultsInquiry',
    children: [
      {
        path: '/resultsInquiry/index',
        component: () => import('../modules/resultsInquiry/views/resultsInquiry')
      }
    ]
  },

  {
    path: '/print',
    component: Layout,
    redirect: '/print/index',
    name: 'Print',
    children: [
      {
        path: '/print/index',
        component: () => import('../modules/printNTS/views/printNTS')
      }
    ]
  },
  {
    path: '/consult',
    component: Layout,
    redirect: '/consult/chat',
    name: 'Consult',
    children: [
      {
        path: 'chat',
        component: () => import('../modules/consult/views/consult')
      }
    ]
  },
  {
    path: '/personalCenter',
    component: Layout,
    redirect: '/personalCenter/index',
    name: 'PersonalCenter',
    children: [
      {
        path: 'index',
        component: () => import('../modules/personalCenter/views/personalCenter')
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
