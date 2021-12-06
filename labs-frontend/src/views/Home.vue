<template>
  <div class="container-fluid mt-2">
    <h1>Bienvenido al dummy</h1>
    <!-- <add-role/> 
    <course/>
    <roles/> -->
    <div class="row">
      <div>
        <button type="button" class="btn btn-primary m-3">
          <router-link :to="{ name: 'courses' }" class="text-center text-white">
            cursos
          </router-link>
        </button>
        <button type="button" class="btn btn-primary m-3">
          <router-link
            :to="{ name: 'add-role' }"
            class="text-center text-white"
          >
            agregar rol
          </router-link>
        </button>
        <button type="button" class="btn btn-primary m-3">
          <router-link :to="{ name: 'roles' }" class="text-center text-white">
            roles de usuario
          </router-link>
        </button>
      </div>
      <div v-if="isTeacher">
        <button type="button" class="btn btn-primary m-3">
          <router-link :to="{ name: 'create' }" class="text-center text-white">
            crear curso</router-link
          >
        </button>
      </div>
      <router-view> </router-view>
    </div>
  </div>
</template>

<script>
import { getAuthenticationToken } from "@/dataStorage";
import axios from "axios";
export default {
  name: "Home",
  // components: {
  //   Roles,
  //   Course,
  //   AddRole
  // },
  data() {
    return {
      roles: [],
      isTeacher: false,
    };
  },
  async beforeCreate() {
    if (!getAuthenticationToken()) {
      this.$router.push({ name: "login" });
    }
    const requestPath = "/mis-roles";
    await axios
      .get(this.$store.state.backURL + requestPath, {
        params: { access_token: getAuthenticationToken() },
      })
      .then((response) => {
        if (response.status !== 200) {
          alert("Error Obteniendo sus roles");
        } else {
          this.roles = response.data;
          if (
            response.data[0].roleName === "Profesor" ||
            (response.data[1] && response.data[1].roleName === "Profesor")
          ) {
            this.isTeacher = true;
          }
        }
      })
      .catch((error) => {
        alert("Error en la petición");
        console.log(error);
      });
  },
};
</script>

<style></style>
