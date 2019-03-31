<template>
  <el-dialog :visible.sync="dialogShow" :title="dialogTitle">
    <el-form :model="formItem" ref="form" label-width="80px">
      <el-form-item label="部门名称">
        <el-input v-model="formItem.deptName"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogShow = false">取 消</el-button>
      <el-button type="primary" @click="save()">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "userForm",
    components: {},
    props: {
      show: {
        type: Boolean,
        default: false
      },
      state: {
        type: String,
        default: 'info'
      },
      code: {
        type: String,
        required: false,
        default: ''
      }
    },
    data() {
      return {
        formItem: {
          deptName: '',
        }
      }
    },
    computed: {
      dialogTitle() {
        switch (this.state) {
          case 'info':
            return '查看部门';
          case 'edit':
            return '编辑部门';
          case 'add':
            return '新增部门';
        }
      },
      dialogShow: {
        get() {
          return this.show;
        },
        set(val) {
          this.$emit('update:show', val);
        }
      }
    },
    watch: {
      dialogShow(newVal) {
        if (newVal) {
          if (this.code) {
            this.$get('/rbac/get-dept', {deptCode: this.code}).then((data) => {
              this.formItem = data;
            });
          } else {
            this.formItem = {};
          }
        }
      }
    },
    methods: {
      save() {
        if (this.state == 'add') {
          this.$put('/rbac/add-dept', {deptName: this.formItem.deptName}).then((data) => {
            this.$message.success('操作成功');
            this.dialogShow = false;
            this.$emit('refresh');
          });
        } else if (this.state === 'edit') {
          this.$put('/rbac/edit-dept', {deptCode: this.code, deptName: this.formItem.deptName}).then((data) => {
            this.$message.success('操作成功');
            this.dialogShow = false;
            this.$emit('refresh');
          });
        }
      }
    }
  }
</script>

<style scoped>

</style>
