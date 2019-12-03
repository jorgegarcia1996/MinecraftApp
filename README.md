# MinecraftApp

## Índice

<a href="#base-de-datos">* Base de datos</a>

## Base de datos

Tabla de bloques.
<img src="./Screenshots/TablaBloques.png">

Tabla de mobs.
<img src="./Screenshots/TablaMobs.png">

Tabla de usuarios.
<img src="./Screenshots/TablaUsuarios.png">

## Internacionalización

La aplicacón está disponible en español y en inglés, por defecto se utilizará el inglés.

## Login

La primera pantalla de la app es el login, el usuario es tu email y hay un botón par acceder al registro.

<img src="./Screenshots/Login.png" width="200px">

## Registro

En el registro hay que rellenar todos los campos y las contraseñas deben ser iguales para poder registrar un usuario nuevo.

<img src="./Screenshots/Registro1.png" width="200px">
<img src="./Screenshots/Registro2.png" width="200px">

Al completar el registro, se regresa a la pantalla de login automáticamente.

<img src="./Screenshots/RegistroCompletado.png" width="200px">

## Página principal

La página principal muestra el logo y una descripción de la app (lorem ipsum).

<img src="./Screenshots/PaginaPrincipal.png" width="200px">

## Bloques

La parte de los bloques es un recycler que coge los datos de firebase database y las imágenes de firebase storage.

<img src="./Screenshots/Recycler1.png" width="200px">

## Mobs

La parte de los mobs es otro recycler que coge los datos de firebase database y las imágenes de firebase storage.

<img src="./Screenshots/Recycler2.png" width="200px">

## Menú Principal

El menú principal tiene 2 opciones, salir de la app e ir al perfil.

<img src="./Screenshots/MenuPrincipal.png" width="200px">

## Perfil

La pantalla de perfil muestra los datos del usuario logueado.

<img src="./Screenshots/PerfilModoLectura.png" width="200px">

Para volver a la actividad principal está el botón al lado del icono de la app en la barra de accion superior.

### Editar perfil

Al clicar en el botón de editar, se habilita la edición del nombre, apellidos y nacionalidad del usuario.

<img src="./Screenshots/PerfilModoEditar.png" width="200px">

Si no quieres guardar los datos editados pulsas en el botón de cancelar y se deshabilita la edición de los datos.

Si clicas en guardar se actualizan los datos en la base de datos y en la pantalla de perfil.

<img src="./Screenshots/PerfilEditado.png" width="200px">

### Borrar perfil

Para eliminar tu cuenta de la aplicación, está el botón de "borrar cuenta", el cual abre una alerta para confirmar el borrado.

<img src="./Screenshots/AletaBorrarCuenta.png" width="200px">

Si se cancela solo se cierra la alerta, pero si confirmamos el borrado de la cuenta, se borra la misma y nos devuelve a la pantalla de login.

<img src="./Screenshots/CuentaBorrada.png" width="200px">
