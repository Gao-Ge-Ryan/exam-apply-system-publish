import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in subMenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if false, the item will hidden in breadcrumb(default is true)
  }
**/
export const constantRouterMap = [
  { path: '/404', component: () => import('@/views/404'), hidden: true },
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  {
    path: '/',
    component: Layout,
    redirect: '/main',
    name: 'Main',
    // hidden: true,
    children: [{
      path: 'main',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'form' }
    }]
  },

  {
    path: '/user',
    component: Layout,
    children: [{
      path: 'index',
      name: 'User',
      component: () => import('@/views/user/index'),
      meta: { title: '用户管理', icon: 'form' }
    }]
  },
  {
    path: '/exam',
    component: Layout,
    children: [{
      path: 'index',
      name: 'Exam',
      component: () => import('@/views/exam/index'),
      meta: { title: '考试管理', icon: 'form' }
    }]
  },
  {
    path: '/examIntroduction',
    component: Layout,
    children: [{
      path: 'index',
      name: 'ExamIntroduction',
      component: () => import('@/views/examIntroduction/index'),
      meta: { title: '考试介绍管理', icon: 'form' }
    }]
  },
  {
    path: '/feedback',
    component: Layout,
    children: [{
      path: 'index',
      name: 'Feedback',
      component: () => import('@/views/feedback/index'),
      meta: { title: '反馈管理', icon: 'form' }
    }]
  },
  {
    path: '/info',
    component: Layout,
    children: [{
      path: 'index',
      name: 'Info',
      component: () => import('@/views/info/index'),
      meta: { title: '考试资讯管理', icon: 'form' }
    }]
  },
  {
    path: '/examUser',
    component: Layout,
    children: [{
      path: 'index',
      name: 'ExamUser',
      component: () => import('@/views/examUser/index'),
      meta: { title: '考试报名管理', icon: 'form' }
    }]
  },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
