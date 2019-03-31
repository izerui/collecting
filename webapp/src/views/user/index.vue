<template>
  <div>
    <div style="margin: 10px;padding-left: 10px;">
      <el-button @click="add()" type="primary">新增</el-button>
    </div>
    <el-table :data="userList">
      <el-table-column type="index" label="序号"></el-table-column>
      <el-table-column prop="avatar" label="头像">
        <template slot-scope="scope">
          <avatar :username="scope.row.userName" :src="scope.row.avatar" :size="40"></avatar>
        </template>
      </el-table-column>
      <el-table-column prop="userName" label="手机号"></el-table-column>
      <el-table-column prop="nickName" label="用户名"></el-table-column>
      <el-table-column prop="roleName" label="角色"></el-table-column>
      <el-table-column prop="recordStatus" label="状态"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="edit(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <user-form :show.sync="form.show" :code="form.code" :state="form.state"></user-form>
  </div>
</template>

<script>
  import Avatar from 'vue-avatar'
  import UserForm from './userForm'

  export default {
    name: 'index',
    components: {
      UserForm,
      Avatar
    },
    data () {
      return {
        userList: [],
        form: {
          show: false,
          state: 'info',
          code: '',
        }
      }
    },
    created () {
      this.listUsers()
    },
    computed: {},
    watch: {
      $route () {
        this.listUsers()
      }
    },
    methods: {
      async listUsers () {
        this.userList = await this.$get('/rbac/list-users')
      },
      edit (item) {
        this.form.state = 'edit'
        this.form.code = item.userCode;
        this.form.show = true;
      },
      add () {
        this.$message.info("djfdsjfdsf");
      }
    }

  }
</script>
