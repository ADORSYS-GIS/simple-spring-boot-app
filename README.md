# Simple Spring Boot App for MCP Showcase

This project serves as a demonstration for using Model-Context-Protocol (MCP) servers to perform advanced code analysis. It showcases the `@vymalo/code-graph-mcp` and `mcp-neo4j-cypher` tools, which work together to index a codebase into a Neo4j graph database and allow for complex queries on the code structure.

## MCP Showcase: Code Analysis with Neo4j

This demo highlights a powerful workflow where instead of manually reading files, the agent interacts with a graph representation of the code.

-   **`@vymalo/code-graph-mcp`**: This MCP server traverses the specified project directory, parses the source code (Java, in this case), and populates a Neo4j database with nodes (classes, methods, interfaces) and relationships (calls, implementations, etc.).
-   **`mcp-neo4j-cypher`**: This MCP server provides a direct interface to query the Neo4j database using Cypher, the graph query language. This allows the agent to ask complex questions about code structure, dependencies, and usage.

### Docker Compose Setup

The `compose.yaml` file orchestrates the necessary services for this demo:

```yaml
services:
  neo4j:
    image: neo4j:5
    ports:
      - "17474:7474"
      - "17687:7687"
    environment:
      NEO4J_dbms_security_procedures_unrestricted: "algo.*,apoc.*"
      NEO4JLABS_PLUGINS: '["apoc"]'
```

This sets up a Neo4j container with the APOC library enabled, which is required by the `@vymalo/code-graph-mcp`.

To start the database, run:
```bash
docker compose up -d
```

### MCP Configuration

The agent's connection to the MCP servers is configured in `.roo/mcp.json`:

```json
{
    "mcpServers": {
        "neo4j-cipher": {
            "command": "uvx",
            "args": [
                "mcp-neo4j-cypher@0.3.0"
            ],
            "env": {
                "NEO4J_URI": "bolt://localhost:17687",
                "NEO4J_USERNAME": "neo4j",
                "NEO4J_PASSWORD": "bitnami1",
                "NEO4J_DATABASE": "neo4j"
            },
            "disabled": false,
            "autoApprove": [
                "read_neo4j_cypher",
                "get_neo4j_schema"
            ],
            "alwaysAllow": [
                "get_neo4j_schema",
                "read_neo4j_cypher"
            ],
            "disabledTools": [
                "write_neo4j_cypher"
            ]
        },
        "code-analyzer": {
            "command": "npx",
            "args": [
                "@vymalo/code-graph-mcp"
            ],
            "disabled": false,
            "alwaysAllow": [
                "run_analyzer"
            ],
            "env": {
                "DEFAULT_DIR": "</path/to/your/project>",
                "NEO4J_URL": "bolt://localhost:17687",
                "NEO4J_USER": "neo4j",
                "NEO4J_PASSWORD": "bitnami1",
                "NEO4J_DATABASE": "neo4j"
            }
        }
    }
}
```
This file tells the agent how to launch the `code-analyzer` and `neo4j-cipher` MCPs using `stdio` for communication.

## Example Prompts

Here are some example prompts you can use to interact with the agent and see the MCPs in action:

**Basic Analysis:**
- "Where is the method `getWorldMes` used? Check using neo4j"
- "Find all controllers in the project."
- "What methods does the `HelloWorldServiceInterface` declare?"

**Refactoring:**
- "Refactor the method `getWorldMes` and rename it to `getGreeting`."
- "Find all implementations of the `HelloWorldServiceInterface` and add a new method `getFarewellMessage` to them."

**Workflow Rules:**
- "How can I streamline the process of asking you to interact with neo4j using these tools? I want to write rules that'll be relevant and useful. Goal is that instead of reading file by file, the agent shall insted scan the neo4j and index it after changes."

## About the Spring Boot Application

The underlying project is a minimal Spring Boot application with a few REST endpoints. Its primary purpose is to provide a simple yet non-trivial codebase for the `code-analyzer` to index.

### Prerequisites

- Java 24
- Maven 3.5+
- Docker and Docker Compose

### Getting Started

1.  **Start the Neo4j database:**
    ```bash
    docker-compose up -d
    ```

2.  **Build the application (optional, for running):**
    ```bash
    ./mvnw clean package
    ```

3.  **Run the application (optional):**
    ```bash
    ./mvnw spring-boot:run
    ```

## License

This project is licensed under the MIT License.