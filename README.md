# Endless-RecyclerView
>Endless support for RecyclerView
 
## Gradle Dependency

  1. Add the JitPack repository to your build file

	```gradle
	allprojects {
				repositories {
					...
					maven { url "https://jitpack.io" }
				}
	}
	```

  2. Add the dependency

 ```gradle
dependencies {
    compile 'com.github.ybq:Endless-RecyclerView:1.0.2'
 }
```


## Usage
 
```java
RecyclerView recyclerView = ...;
View loadingView = ...;
Endless endless = Endless.applyTo(recyclerView,
                loadingView
        );
endless.setLoadMoreListener(new Endless.LoadMoreListener() {
            @Override
            public void onLoadMore(int page) {
                .....;
            }
        });
```

## Acknowledgements
- [https://gist.github.com/mipreamble/b6d4b3d65b0b4775a22e](https://gist.github.com/mipreamble/b6d4b3d65b0b4775a22e).



