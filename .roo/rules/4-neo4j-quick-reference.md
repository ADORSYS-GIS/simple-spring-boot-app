# Neo4j Quick Reference for Code Analysis

## Essential Commands

### 1. Start Any Code Analysis Task
```
Run code analyzer first:
- Use code-analyzer tool with resetDb: true
- This ensures fresh, up-to-date data
```

### 2. Find Method Definitions and Usage
```cypher
// Find specific method
MATCH (m:JavaMethod) WHERE m.name = 'methodName'
RETURN m.name, m.filePath, m.startLine, m.endLine, m.parentId

// Find methods containing text
MATCH (m:JavaMethod) WHERE m.name CONTAINS 'partialName'
RETURN m.name, m.filePath, m.startLine ORDER BY m.name
```

### 3. Analyze Class Structure
```cypher
// All classes and their methods
MATCH (c:JavaClass)-[:HAS_METHOD]->(m:JavaMethod)
RETURN c.name, c.filePath, m.name ORDER BY c.name

// Controllers only
MATCH (c:JavaClass)-[:HAS_METHOD]->(m:JavaMethod)
WHERE c.name CONTAINS 'Controller'
RETURN c.name, m.name, m.filePath, m.startLine
```

### 4. Find Interfaces and Implementations
```cypher
// All interfaces
MATCH (i:JavaInterface)-[:HAS_METHOD]->(m:JavaMethod)
RETURN i.name, i.filePath, m.name

// Classes implementing interfaces
MATCH (c:JavaClass) WHERE c.name CONTAINS 'Service'
RETURN c.name, c.filePath, c.qualifiedName
```

### 5. Project Overview
```cypher
// File structure
MATCH (f:File)-[:DEFINES_CLASS|DEFINES_INTERFACE]->(entity)
RETURN f.filePath, entity.name, labels(entity)

// Test files
MATCH (f:File) WHERE f.filePath CONTAINS 'test'
RETURN f.filePath, f.name
```

## Refactoring Workflow Template

1. **Find what to change**:
   ```cypher
   MATCH (m:JavaMethod) WHERE m.name = 'oldMethodName'
   RETURN m.filePath, m.startLine, m.parentId
   ```

2. **Find where it's used** (check controllers, services, tests):
   ```cypher
   MATCH (c:JavaClass)-[:HAS_METHOD]->(m:JavaMethod)
   WHERE c.name =~ '.*Controller|.*Service|.*Test'
   RETURN c.name, c.filePath, m.name
   ```

3. **Make changes** to all identified files

4. **Re-index**: Run code-analyzer again

5. **Verify**:
   ```cypher
   // Check new method exists
   MATCH (m:JavaMethod) WHERE m.name = 'newMethodName'
   RETURN m.name, m.filePath
   
   // Check old method is gone
   MATCH (m:JavaMethod) WHERE m.name = 'oldMethodName'
   RETURN m.name, m.filePath
   ```

## Key Principles
- **Query before reading files**
- **Always re-index after changes**
- **Use Neo4j to find all dependencies**
- **Verify changes with Neo4j queries**