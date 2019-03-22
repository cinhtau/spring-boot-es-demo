
Create Index 

```
PUT demo
{
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 0
  }, 
  "mappings": {
    "doc": {
      "properties": {
        "jobAd": {
          "properties": {
            "jobTitle": {
              "type": "text"
            }
          }
        },
        "favouriteItem": {
          "properties": {
            "note": {
              "type": "text"
            }
          }
        },
        "jobAdRelations": {
          "type": "join",
          "relations": {
            "jobAd": "favouriteItem"
          }
        }
      }
    }
  }
}
```

Delete Index
```
DELETE demo
```