# 3scale-logger-audit

Este projeto utiliza o quarkus para realizar a leitura no banco de dados do 3scale e expor os logs de audit utilizando o elasticsearch como frontend.

Esta aplicação utiliza um arquivo para salvar o número de linhas que já foram lidas e enviadas para os logs do elasticsearch.

Para armazenar este arquivo de forma persistente é necessário criar a estrutura de armazenamento para a persistencia composta de um pv e um pvc. Para isso é necessário realizar a criação do pvc basta aplicar o yaml que está no diretorio raiz do projeto.

```bash
oc create -f pvc.yaml
```

e posterior realizar a configuração do pvc na aplicação quarkus.

```bash
quarkus.openshift.pvc-volumes.my-volume.claim-name=<volume-name>
quarkus.openshift.mounts.my-volume.path=/deployments/mnt/
```

Quando o pvc for criado e a aplicação começar o consumo do recurso, o tipo utilizado do pvc realizará o bind automático para o pv, permitindo realizar o armazenamento.

Posterior a isso é necessário realizar a configuração do banco de dados por meio de algumas propriedades do application.properties essas configurações são obtidas no secret "system-database" system-database encontrado no projeto em que o 3scale foi instalado.

```
quarkus.datasource.db-kind=mysql
quarkus.datasource.username=<username-from-secret-system-database>
quarkus.datasource.password=<passwoerd-from-secret-system-database>
quarkus.datasource.jdbc.url=<jdbc:mysql://mysql-service/system>
quarkus.hibernate-orm.database.generation=validate
```

Em seguida precisamos realizar o deploy da aplicação quarkus é necessário configurar a tag:

```bash
quarkus.openshift.deploy=true
```

dentro de `application.properties`, em seguida basta executar o comando abaixo para realizar o deploy no namespace desejado.

```bash
quarkus build
```

Caso apresente um `crash loop back-off` há utilizar a imagem do registry interno, há a possibilidade de realizar o deploy através do build da imagem utilizando podman. para isso é necessário comentar a tag: `quarkus.openshift.deploy=true`

E realizar o build do projeto quarkus, posterior ao build é necessário realizar a disponibilização da imagem no quay.io para utilizar-la para deploy da aplicação. Para isso é necessário realizar o build da imagem por meio do Dockerfile na raiz do projeto publicar-la seguindo os passos a baixo.

```bash
podman build -t quay.io/<seu_usuario_quay>/<nome_imagem>:<tag> .

podman login -u <user> -p <password> quay.io

podman push quay.io/<seu_usuario_quay>/<nome_imagem>:<tag>

```

É necessário realizar a exposição do repository para visibilidade da imagem para isso acesse o seu repository->settings->make it public.

![Transformar o repository para publico](/images/quay.png)

Posterior basta realizar o deploy no openshift através da imagem no quay.

```bash
oc new-app --image=quay.io/<seu_usuario_quay>/<nome_imagem>:<tag>
```

A cada 2 minutos é feito a leitura de novos logs e adicionada ao elasticsearch.

Para a vizualização dos logs é necessário realizar a configuração do elasticsearch e kibana caso não tenham sido configurados anteriormente. Para isso é necessário realizar a instalação de dois operators: "OpenShift Elasticsearch Operator" e "Red Hat OpenShift Logging".
Para realiar a instalação basta acessar o menu de Operators Hub e pesquisar por ambos operators.

<p align="center">
  <img src="/images/Operators.png" alt="Menu operators">
   <img src="/images/Operators-logging.png" alt="Operators para serem instalados">
</p>

Basta aceitar as configiurações default do operator e em seguida realizar a instalação do ClusterLoggig applicando o arquivo [Cluster Logging.yaml](CustomObjects/ClusterLogging.yaml)

```bash
oc create -f CustomObjects/ClusterLogging.yaml
```

Após esta instalação é criada uma rota para o serviço do kibana e é preciso configurar um index para visualização das metricas. Para isso basta criar o index acessando o menu em: Management-> Index Patterns -> Create Index Pattern.

Para a vizualização dos logs é criado o index da seguinte forma `*apps`.

![Index Kibana](/images/IndexPattern.png)

E assim os logs passam a ser observados na interface do kibana.
![Index Kibana](/images/LogsKibana.png)

# Referencias:

https://docs.openshift.com/container-platform/4.16/observability/logging/cluster-logging-deploying.html
