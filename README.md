# Endless-RecyclerView
>Endless support for RecyclerView
 
## Gradle Dependency

    

```gradle
 dependencies {
    compile 'com.github.ybq:Endless-RecyclerView:1.0.4'
 }
```


## Usage
 
```java
RecyclerView recyclerView = ...;
Endless endless = Endless.applyTo(recyclerView,
                new LoadingView()
        );
endless.setLoadMoreListener(new Endless.LoadMoreListener() {
            @Override
            public void onLoadMore(int page) {
                .....
                endless.loadMoreComplete();
            }
        });
```

## Acknowledgements
- [https://gist.github.com/mipreamble/b6d4b3d65b0b4775a22e](https://gist.github.com/mipreamble/b6d4b3d65b0b4775a22e).

## License
The MIT License (MIT) 

Copyright © 2016 ybq

