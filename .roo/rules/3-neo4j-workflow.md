# Neo4j Workflow Rules

## Core Principle
**Always use Neo4j for code analysis instead of reading files individually.** The Neo4j database contains the complete code structure and relationships, making it faster and more efficient than file-by-file analysis.

## Workflow Steps

### 1. Initial Analysis
- **ALWAYS** run the code analyzer first when starting any code analysis task
- Use `code-analyzer` tool with `resetDb: true` to ensure fresh data
- This populates Neo4j with current codebase structure

### 2. Query Strategy
- Use `neo4j-cipher` for complex queries and relationships
- Use `neo4j-memory` for simpler searches and entity lookups
- Query the database to understand code structure before making changes

### 3. Common Query Patterns

#### Finding Method Usage
```cypher
// Find method definitions
MATCH (m:JavaMethod) WHERE m.name = 'methodName' 
RETURN m.name, m.filePath, m.startLine, m.endLine, m.parentId

// Find classes that might use a method (by examining their methods)
MATCH (c:JavaClass)-[:HAS_METHOD]->(m:JavaMethod) 
WHERE c.name CONTAINS 'Controller' OR c.name CONTAINS 'Service'
RETURN c.name, m.name, m.filePath, m.startLine
```

#### Finding Class Relationships
```cypher
// Find all classes and their methods
MATCH (c:JavaClass)-[:HAS_METHOD]->(m:JavaMethod) 
RETURN c.name, c.filePath, m.name ORDER BY c.name

// Find interfaces and implementations
MATCH (i:JavaInterface)-[:HAS_METHOD]->(m:JavaMethod)
RETURN i.name, i.filePath, m.name
```

#### Finding File Structure
```cypher
// Get project overview
MATCH (f:File)-[:DEFINES_CLASS|DEFINES_INTERFACE]->(entity)
RETURN f.filePath, entity.name, labels(entity)

// Find test files
MATCH (f:File) WHERE f.filePath CONTAINS 'test'
RETURN f.filePath, f.name
```

### 4. Refactoring Workflow
1. **Query first**: Use Neo4j to find all occurrences of what needs to be changed
2. **Plan changes**: Identify all files that need modification based on Neo4j results
3. **Make changes**: Apply changes to files systematically
4. **Re-index**: Run analyzer again to update Neo4j with changes
5. **Verify**: Query Neo4j to confirm changes were applied correctly

### 5. When to Re-run Analyzer
- After any code modifications (method renames, class changes, etc.)
- Before starting a new analysis task
- When Neo4j queries return unexpected or empty results
- After major refactoring operations

### 6. Efficiency Rules
- **DON'T** read files individually when you can query Neo4j
- **DO** use Neo4j to understand code relationships and dependencies
- **DO** use Neo4j to find all usages before making changes
- **DO** verify changes by querying Neo4j after modifications

### 7. Query Examples for Common Tasks

#### Method Refactoring
```cypher
// Step 1: Find all method definitions
MATCH (m:JavaMethod) WHERE m.name = 'oldMethodName'
RETURN m.filePath, m.startLine, m.parentId

// Step 2: Find potential callers (controllers, services, tests)
MATCH (c:JavaClass)-[:HAS_METHOD]->(m:JavaMethod)
WHERE c.name =~ '.*Controller|.*Service|.*Test'
RETURN c.name, c.filePath, m.name
```

#### Dependency Analysis
```cypher
// Find all classes in a package
MATCH (c:JavaClass) WHERE c.qualifiedName CONTAINS 'package.name'
RETURN c.name, c.filePath, c.qualifiedName

// Find all methods in specific classes
MATCH (c:JavaClass)-[:HAS_METHOD]->(m:JavaMethod)
WHERE c.name IN ['Class1', 'Class2']
RETURN c.name, m.name, m.filePath, m.startLine
```

## Benefits
- **Speed**: Neo4j queries are faster than reading multiple files
- **Completeness**: See all relationships and dependencies at once
- **Accuracy**: Avoid missing references when refactoring
- **Context**: Understand code structure before making changes